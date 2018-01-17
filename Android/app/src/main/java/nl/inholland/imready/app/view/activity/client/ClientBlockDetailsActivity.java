package nl.inholland.imready.app.view.activity.client;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.events.BlockDetailViewEvent;
import nl.inholland.imready.app.presenter.client.ClientBlockDetailsPresenter;
import nl.inholland.imready.app.presenter.client.ClientBlockDetailsPresenterImpl;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.adapter.PersonalComponentExpandableListAdapter;
import nl.inholland.imready.app.view.fragment.HandInActivityDialogFragment;
import nl.inholland.imready.app.view.listener.DialogListener;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.blocks.PersonalComponent;
import nl.inholland.imready.model.enums.BlockPartStatus;

import static br.com.zbra.androidlinq.Linq.stream;

public class ClientBlockDetailsActivity extends AppCompatActivity implements ClientBlockDetailsView, DialogListener {

    private ClientBlockDetailsPresenter presenter;
    private PersonalComponentExpandableListAdapter adapter;
    private PersonalBlock block;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_block_details);

        presenter = new ClientBlockDetailsPresenterImpl(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save
        List<PersonalComponent> adapterData = adapter.getData();
        List<PersonalActivity> activities = stream(adapterData)
                .selectMany(PersonalComponent::getActivities)
                .where(value -> value.getStatus() == BlockPartStatus.ONGOING)
                .toList();
        for (PersonalActivity activity : activities) {
            presenter.saveActivity(activity);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        EventBus.getDefault().postSticky(new BlockDetailViewEvent(block, null));
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onBlockDetailViewEvent(BlockDetailViewEvent event) {
        block = event.getBlock();
        if (block == null) {
            Toast.makeText(this, "Something went wrong whilst loading the block data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set action bar title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(block.getName());
        }

        // Setup expandable list
        ExpandableListView expandableListView = findViewById(R.id.blockcomponents);
        expandableListView.setClickable(true);
        // order by the closest deadline
        List<PersonalComponent> components = block.getComponents();
        adapter = new PersonalComponentExpandableListAdapter(this, components, presenter);
        expandableListView.setAdapter(adapter);

        // possibly extending a group based on data passed
        PersonalComponent component = event.getComponent();
        if (component == null) {
            expandableListView.expandGroup(0);
        } else {
            int index = block.getComponents().indexOf(component);
            expandableListView.expandGroup(index);
        }

        // clear event
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public void showHandInDialog(PersonalActivity activity) {
        HandInActivityDialogFragment dialog = new HandInActivityDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ParcelableConstants.ACTIVITY, activity);
        dialog.setArguments(arguments);
        dialog.show(getSupportFragmentManager(), "handin");
    }

    @Override
    public void showSucces() {
        showMessage(getString(R.string.activity_handed_in));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Bundle arguments = dialog.getArguments();
        if (arguments != null) {
            PersonalActivity activity = arguments.getParcelable(ParcelableConstants.ACTIVITY);
            presenter.putActivity(activity, BlockPartStatus.PENDING);
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //ignore
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog) {
        //ignore
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        //ignore
    }
}
