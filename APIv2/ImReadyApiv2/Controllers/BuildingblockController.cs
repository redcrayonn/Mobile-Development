using ImReady.Data.Models;
using ImReady.Service.Services;
using ImReadyApiv2.Models.Input;
using ImReadyApiv2.Results;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Results;

namespace ImReadyApiv2.Controllers
{
    public class BuildingblockController : ApiController
    {
        private readonly IBuildingblockService _blockService;

        public BuildingblockController(IBuildingblockService blockService)
        {
            _blockService = blockService;
        }

        // GET: api/Buildingblock/
        public IHttpActionResult Get()
        {
            //Get Id of logged in client
            var blocks = _blockService.getAll().ToList();
            List<BuildingblockResult> blockresult= new List<BuildingblockResult>();

            foreach (var block in blocks)
            {
                blockresult.Add(new BuildingblockResult(block));
            }

            if (blockresult != null)
            {
                return Ok(blockresult);
            }
            else
            {
                return new StatusCodeResult(HttpStatusCode.InternalServerError, this);
            }
        }

        // GET: api/Buildingblock/1
        public IHttpActionResult Get(string id)
        {
            //Get Id of logged in client
            BuildingblockResult blockresult = new BuildingblockResult(_blockService.getById(id));

            if (blockresult != null)
            {
                return Ok(blockresult);
            }
            else
            {
                return new StatusCodeResult(HttpStatusCode.InternalServerError, this);
            }
        }

        // POST: api/
        public void Post([FromBody]PostBuildingblockInputModel model)
        {
            Buildingblock block = model.GetBlock();
            var result = _blockService.AddBlock(block);
        }
    }
}
