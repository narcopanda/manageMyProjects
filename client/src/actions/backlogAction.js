import axios from "axios";
import {
  Get_Errors,
  Get_Backlog,
  Get_Project_Task,
  Delete_Project_Task
} from "./types";

export const addProjectTask = (
  backlog_id,
  project_task,
  history
) => async dispatch => {
  try {
    await axios.post(`/api/backlog/${backlog_id}`, project_task);
    history.push(`/projectBoard/${backlog_id}`);
    dispatch({
      type: Get_Errors,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: Get_Errors,
      payload: err.response.data
    });
  }
};

export const getBacklog = backlog_id => async dispatch => {
  try {
    const res = await axios.get(`/api/backlog/${backlog_id}`);
    dispatch({
      type: Get_Backlog,
      payload: res.data
    });
  } catch (err) {
    dispatch({
      type: Get_Errors,
      payload: err.response.data
    });
  }
};

export const getProjectTask = (
  backlog_id,
  pt_id,
  history
) => async dispatch => {
  try {
    const res = await axios.get(`/api/backlog/${backlog_id}/${pt_id}`);
    dispatch({
      type: Get_Project_Task,
      payload: res.data
    });
  } catch (err) {
    console.log(err);
    history.push("/dashboard");
  }
};

export const updateProjectTask = (
  backlog_id,
  pt_id,
  project_task,
  history
) => async dispatch => {
  try {
    await axios.patch(`/api/backlog/${backlog_id}/${pt_id}`, project_task);
    history.push(`/projectBoard/${backlog_id}`);
    dispatch({
      type: Get_Errors,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: Get_Errors,
      payload: err.response.data
    });
  }
};

export const deleteProjectTask = (backlog_id, pt_id) => async dispatch => {
  if (
    window.confirm(
      `You are deleting project task ${pt_id}, this action cannot be undone`
    )
  ) {
    await axios.delete(`/api/backlog/${backlog_id}/${pt_id}`);
    dispatch({
      type: Delete_Project_Task,
      payload: pt_id
    });
  }
};
