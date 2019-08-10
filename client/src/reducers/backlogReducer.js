import {
  Get_Backlog,
  Get_Project_Task,
  Delete_Project_Task
} from "../actions/types";

const initState = {
  project_tasks: [],
  project_task: {}
};

export default function(state = initState, action) {
  switch (action.type) {
    case Get_Backlog:
      return {
        ...state,
        project_tasks: action.payload
      };

    case Get_Project_Task:
      return {
        ...state,
        project_task: action.payload
      };

    case Delete_Project_Task:
      return {
        ...state,
        project_tasks: state.project_tasks.filter(
          project_task => project_task.projectSeq !== action.payload
        )
      };

    default:
      return state;
  }
}
