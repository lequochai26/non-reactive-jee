package org.lequochai.non_reactive_jee.rest;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.lequochai.non_reactive_jee.models.Employee;
import org.lequochai.non_reactive_jee.models.EmployeeContainer;
import org.lequochai.non_reactive_jee.response.Response;

@Path ("/employee")
public class EmployeeRestfulApi {
    // Constructors:
    public EmployeeRestfulApi() {

    }

    // Methods:
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response<List<Employee>> getAllEmployees(@QueryParam ("keyword") String keyword) {
        Response<List<Employee>> response = new Response<>();
        response.setSuccess(true);
        
        if (keyword == null) {
            response.setResult(
                EmployeeContainer.getInstance()
                    .getAll()
            );
        }
        else {
            response.setResult(
                EmployeeContainer.getInstance()
                    .get(keyword)  
            );
        }

        return response;
    }

    @GET
    @Path ("/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Employee> getEmployee(@PathParam("id") String idStr) {
        Response<Employee> response = new Response<>();

        int id;
        try {
            id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(
                "ID must be an integer!"
            );
            response.setCode("ID_INVALID");
            return response;
        }

        response.setSuccess(true);
        response.setResult(
            EmployeeContainer
                .getInstance()
                .get(id)  
        );

        return response;
    }

    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> insertEmployee(Employee employee) {
        Response<Void> response = new Response<>();

        try {
            EmployeeContainer
                .getInstance()
                .insert(employee);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return response;
        }

        response.setSuccess(true);

        return response;
    }

    @PUT
    @Path ("/{id}")
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> updateEmployee(@PathParam ("id") String idStr, Employee employee) {
        Response<Void> response = new Response<>();

        EmployeeContainer
                .getInstance()
                .update(employee);

        response.setSuccess(true);

        return response;
    }

    @DELETE
    @Path ("/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> deleteEmployee(@PathParam ("id") String idStr) {
        Response<Void> response = new Response<>();

        int id;
        try {
            id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("ID must be an integer!");
            return response;
        }

        try {
            EmployeeContainer
                .getInstance()
                .delete(id);
            response.setSuccess(true);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return response;
        }

        return response;
    }
}
