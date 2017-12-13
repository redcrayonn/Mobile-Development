using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Enums
{
    public enum NotificationType
    {
        NEW = 0,
        CHANGED_BLOCK = 100,
        CHANGED_COMPONENT = 101,
        CHANGED_ACTIVITY = 102,
        APPROVED = 200,
        DECLINED = 300
    }
}
