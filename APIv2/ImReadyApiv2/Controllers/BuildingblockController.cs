using ImReady.Data.Models;
using ImReady.Service.Services;
using ImReady.Service.Services.Interfaces;
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
	[RoutePrefix("api/buildingblock")]
    public class BuildingblockController : ApiController
    {
        private readonly IBuildingblockService _blockService;
		private readonly IComponentService _componentService;

		public BuildingblockController (IBuildingblockService blockService, IComponentService componentService)
        {
            _blockService = blockService;
			_componentService = componentService;
        }

        // GET: api/Buildingblock/
        /// <summary>
        /// Gets all the generic building blocks with its components.
        /// </summary>
        /// <response code="200">OK</response>
        /// <response code="500">InternalServerError</response>
        [ResponseType(typeof(List<BuildingBlockResult>))]
        public IHttpActionResult Get()
        {
            //Get Id of logged in client
            var blocks = _blockService.getAll().ToList();
            List<BuildingBlockResult> blockresult= new List<BuildingBlockResult>();

            foreach (var block in blocks)
            {
                blockresult.Add(new BuildingBlockResult(block, true));
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
        [ResponseType(typeof(BuildingBlockResult))]
        public IHttpActionResult Get(string id)
        {
            //Get Id of logged in client
            BuildingBlockResult blockresult = new BuildingBlockResult(_blockService.getById(id), true);

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
		/// <response code="204">No content</response>
		public void Post([FromBody]PostBuildingblockInputModel model)
        {
            Buildingblock block = model.GetBlock();
            var result = _blockService.AddBlock(block);
        }

		/// <summary>
		/// Edit a existing building block
		/// </summary>
		/// <param name="model"></param>
		/// <response code="204">No content</response>
		[Route("{buildingBlockId}")]
		public IHttpActionResult Put (string buildingBlockId, [FromBody] PostBuildingblockInputModel model) {
			Buildingblock block = _blockService.getById(buildingBlockId);

			if (block == null) {
				return NotFound();
			}

			_blockService.EditBlock(block);
			
			return Ok();
		}

		/// <summary>
		/// Create a new component to a existing building block
		/// </summary>
		/// <remarks>Create a new component for a existing building block. <br/>
		/// You can specify the id in the Body of the request. <br/>
		/// Make sure you prefix the id with the id of the buildingBlock.</remarks>
		/// <param name="model"></param>
		/// <response code="204">No content</response>
		/// <response code="400">Bad request</response>
		/// <response code="404">Not found</response>
		[Route("{buildingBlockId}/component")]
		public IHttpActionResult Post (string buildingBlockId, [FromBody]PostComponentInputModel model) {
			Buildingblock block = _blockService.getById(buildingBlockId);

			if (block == null) {
				return NotFound();
			}

			Component existingComponent = _componentService.Get(model.Id);
			if (existingComponent != null) {
				return BadRequest("Component already exists!");
			}

			if (!ModelState.IsValid) {
				return BadRequest();
			}

			Component component = model.GetModel(block);
			_componentService.Create(component);

			return Ok();
		}

		/// <summary>
		/// Edit a existing component for a building block
		/// </summary>
		/// <param name="model"></param>
		/// <response code="204">No content</response>
		/// <response code="400">Bad request</response>
		/// <response code="404">Not found</response>
		[Route("{buildingBlockId}/component/{componentId}")]
		public IHttpActionResult Put (string buildingBlockId, string componentId, [FromBody]PostComponentInputModel model) {
			Buildingblock block = _blockService.getById(buildingBlockId);
			Component component = _componentService.Get(componentId);
			if (block == null) {
				return NotFound();
			}

			if (!ModelState.IsValid) {
				return BadRequest();
			}

			component = model.GetModel(block);
			bool isSuccess = _componentService.Update(component);

			if (!isSuccess) {
				return BadRequest("Failed to update the model");
			}

			return Ok();
		}

		//TODO add/edit activities
	}
}
