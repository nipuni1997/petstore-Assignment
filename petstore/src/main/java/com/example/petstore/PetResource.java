package com.example.petstore;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/pets")
@Produces("application/json")
public class PetResource {

	private static List<Pet> pets = new ArrayList<>();

	//Get All Pets
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet")))})
	@GET
	public Response getPets() {
		List<Pet> allPets = new ArrayList<Pet>();
		if(pets.size() > 0){
			for(int i=0; i< pets.size(); i++){
				Pet pet = new Pet();
				pet.setPetId(pets.get(i).getPetId());
				pet.setPetAge(pets.get(i).getPetAge());
				pet.setPetName(pets.get(i).getPetName());
				pet.setPetType(pets.get(i).getPetType());
				allPets.add(i, pet);
			}
		}
		return Response.ok(allPets).build();
	}

	//Add a New Pet
	@APIResponses(value = {
			@APIResponse(responseCode = "201", description = "Pet Added", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet")))})
	@POST
	@Path("/add")
	public Response createPet(Pet pet) {
		int newPetID = 0;
		if(pets.isEmpty()) {
			newPetID = 1;
		} else {
			newPetID = pets.get(pets.size()-1).getPetId()+1;
		}

		pet.setPetId(newPetID);
		pet.setPetAge(pet.getPetAge());
		pet.setPetName(pet.getPetName());
		pet.setPetType(pet.getPetType());
		pets.add(pet);
		return Response.ok(pet).build();
	}


	//Find a pet from petID
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet found Successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.")})
	@GET
	@Path("/{petId}")
	public Response getPet(@PathParam("petId") int petId) {
		int value = -1;
		if(pets.size() > 0 && petId > 0){
			for(int i=0; i< pets.size(); i++){
				if(pets.get(i).getPetId() == petId){
					value = i;
				}
			}
		}
		Pet pet = new Pet();
		if(value != -1){
			pet.setPetId(pets.get(value).getPetId());
			pet.setPetAge(pets.get(value).getPetAge());
			pet.setPetName(pets.get(value).getPetName());
			pet.setPetType(pets.get(value).getPetType());
		} else {
			return Response.status(404).build();
		}
		return Response.ok(pet).build();
	}


	//Update an existing pet from petID
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet update Successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.")})
	@PUT
	@Path("/{petId}")
	public Response updatePet(@PathParam("petId") int petId, Pet pet) {
		int value = -1;
		if(pets.size() > 0 && petId > 0){
			for(int i=0; i< pets.size(); i++){
				if(pets.get(i).getPetId() == petId){
					value = i;
				}
			}
		}
		Pet exPet = pets.get(value);
		if(value != -1){
			exPet.setPetAge(pet.getPetAge());
			exPet.setPetName(pet.getPetName());
			exPet.setPetType(pet.getPetType());
		} else {
			return Response.status(404).build();
		}
		return Response.ok(exPet).build();
	}

	//Delete an existing pet
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet Deleted Succesfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.")})
	@DELETE
	@Path("/{petId}")
	public Response deletePet(@PathParam("petId") int petId) {
		int value = -1;
		if(pets.size() > 0 && petId > 0){
			for(int i=0; i< pets.size(); i++){
				if(pets.get(i).getPetId() == petId){
					value = i;
				}
			}
		}
		if(value != -1){
			pets.remove(value);
		} else {
			return Response.status(404).build();
		}
		return Response.status(200).build();
	}
}