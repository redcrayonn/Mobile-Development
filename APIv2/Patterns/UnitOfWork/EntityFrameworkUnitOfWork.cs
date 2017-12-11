using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Patterns.UnitOfWork
{
    public class EntityFrameworkUnitOfWork : IUnitOfWork
    {
        protected DbContext _context;

        public EntityFrameworkUnitOfWork(DbContext context)
        {
            _context = context;
        }

        public virtual void Commit()
        {
            _context.SaveChanges();
        }
        public virtual async void CommitAsync()
        {
            await _context.SaveChangesAsync();
        }

        public virtual void RejectChanges()
        {
            foreach (var entry in _context.ChangeTracker.Entries()
              .Where(e => e.State != EntityState.Unchanged))
            {
                switch (entry.State)
                {
                    case EntityState.Added:
                        entry.State = EntityState.Detached;
                        break;
                    case EntityState.Modified:
                    case EntityState.Deleted:
                        entry.Reload();
                        break;
                }
            }
        }

        public virtual void Dispose()
        {
            _context.Dispose();
        }

    }
}
