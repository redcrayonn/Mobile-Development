using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Patterns.Repository
{
    public class EntityFrameworkGenericRepository<T> : IRepository<T> where T : class
    {
        private readonly DbContext _context;

        private IDbSet<T> _dbSet => _context.Set<T>();
        public IQueryable<T> Entities => _dbSet;

        public EntityFrameworkGenericRepository(DbContext context)
        {
            _context = context;
        }

        public void Add(T entity)
        {
            _dbSet.Add(entity);
        }

        public void Remove(T entity)
        {
            _dbSet.Remove(entity);
        }
    }
}
