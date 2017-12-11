using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Patterns.UnitOfWork
{
    public interface IUnitOfWork : IDisposable
    {
        /// <summary>
        /// Commits all changes
        /// </summary>
        void Commit();
        /// <summary>
        /// Commits all changes asynchronously
        /// </summary>
        void CommitAsync();
        /// <summary>
        /// Discards all changes that has not been commited
        /// </summary>
        void RejectChanges();
    }
}
