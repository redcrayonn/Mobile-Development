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
using System.Web.Http.Description;
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
        /// <summary>
        /// Gets all the generic building blocks with its components.
        /// </summary>
        /// <response code="200">OK</response>
        /// <response code="500">InternalServerError</response>
        [ResponseType(typeof(List<BuildingblockResult>))]
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

        /// <summary>
        /// Gets a single buildingblock with its components
        /// </summary>
        /// <param name="id"></param>
        /// <response code="200">OK</response>
        /// <response code="500">InternalServerError</response>
        [ResponseType(typeof(BuildingblockResult))]
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

        /// <summary>
        /// Add a new building block
        /// </summary>
        /// <param name="model"></param>
        public void Post([FromBody]PostBuildingblockInputModel model)
        {
            Buildingblock block = model.GetBlock();
            var result = _blockService.AddBlock(block);
        }
    }
}
