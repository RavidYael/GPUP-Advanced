package ProcessesManagment.TasksUploadServer.servlets;


import DataManagment.GraphsManager;
import ProcessesManagment.ProcessesManager;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.GraphsDataDTOs.GraphInfoDTO;
import utils.ServletUtils;
import utils.ProccecesDTOs.CompilationTaskDTO;
import utils.ProccecesDTOs.SimulationTaskDTO;

import java.io.IOException;

@WebServlet(name = "TasksUploadServlet", urlPatterns = "/upload-task")
public class TaskUploadServlet extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // TODO I HOPE GSON CREATE NEW OBJECT, OTHERWISE THE TASKS ARE LOCALLY TO THIS SCOPE AND WE LOSE THEM WHEN INSERTING TO THE MAPS
        // ITS NOT THAT COMPLICATED IT JUST THAT WE NEED TO CREATE A A CONSTRUCTOR WHICH I DONT HAVE ZAIN TO CREATE RIGHT NOW

        ProcessesManager missionsManager = ServletUtils.getMissionsManager(getServletContext());

        if(req.getHeader("simulation") != null) //Uploaded simulation task
        {
            SimulationTaskDTO newTaskInfo = gson.fromJson(req.getReader(), SimulationTaskDTO.class);
            if(!missionsManager.isMissionExists(newTaskInfo.getTaskName())) //No task with the same name was found
            {
                missionsManager.addSimulationTask(newTaskInfo);

                resp.addHeader("message", "The task " + newTaskInfo.getTaskName() + " uploaded successfully!");
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                GraphsManager graphsManager = ServletUtils.getGraphsManager(getServletContext());
                missionsManager.addMissionsDTO(newTaskInfo,graphsManager);
            }
            else //A task with the same name already exists in the system
            {
                resp.addHeader("message", "The task " + newTaskInfo.getTaskName() + " already exists in the system!");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        else if(req.getHeader("compilation") != null) //Uploaded compilation task
        {
            CompilationTaskDTO newTaskInfo = gson.fromJson(req.getReader(), CompilationTaskDTO.class);
            if(!missionsManager.isMissionExists(newTaskInfo.getTaskName())) //No task with the same name was found
            {
                missionsManager.addCompilationTask(newTaskInfo);

                resp.addHeader("message", "The task " + newTaskInfo.getTaskName() + " uploaded successfully!");
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                GraphsManager graphsManager = ServletUtils.getGraphsManager(getServletContext());
                missionsManager.addMissionsDTO(newTaskInfo,graphsManager);
            }
            else //A task with the same name already exists in the system
            {
                resp.addHeader("message", "The task " + newTaskInfo.getTaskName() + " already exists in the system!");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        else //invalid header for uploading new task to system
        {
            resp.addHeader("message", "Error in uploading task to server!");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

