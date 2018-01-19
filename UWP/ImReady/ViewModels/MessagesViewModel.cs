using ImReady.Helpers;
using ImReady.Models;
using ImReady.Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.ViewModels
{
    public class MessagesViewModel : INotifyPropertyChanged
    {
        public static MessagesViewModel SingleInstance => new MessagesViewModel();

        public MessagesViewModel()
        {
            LoadClient();
        }
        private ObservableCollection<Message> _messages;

        public ObservableCollection<Message> Messages
        {
            get
            {
                return _messages;
            }
            set
            {
                _messages = value;
                PropertyChanged?.Invoke(this, new PropertyChangedEventArgs("Messages"));
            }
        }


        //Property naam als 'Client' zorgt voor een Access Violation, geen idee waarom.
        public Client ClientProperty { get; set; }

        public Chat ChatProperty { get; set; }

        private string _caregiverName;
        public string CaregiverName
        {
            get
            {
                return _caregiverName;
            }
            set
            {
                _caregiverName = value;
                PropertyChanged?.Invoke(this, new PropertyChangedEventArgs("CaregiverName"));
            }
        }

        public event PropertyChangedEventHandler PropertyChanged;

        public async void LoadClient()
        {
            var teststring = CurrentUser.SingleInstance.Id;
            Client client = await new ClientService().GetClient(teststring);
            ClientProperty = client;
            CaregiverName = $"{client.Caregiver.FirstName} {client.Caregiver.LastName}";
            LoadChat();
        }

        public async void LoadChat()
        {
            Chat Chat = await new MessagingService().GetChat(CurrentUser.SingleInstance.Id, ClientProperty.Caregiver.Id);
            ChatProperty = Chat;
            Messages = new ObservableCollection<Message>();
            foreach(var message in Chat.Messages)
            {
                Messages.Add(message);
            }
        }
    }
}
