import { Get_Projects, Get_Project, Delete_Project } from "../actions/types";

const initialState = {
  projects: [],
  project: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case Get_Projects:
      return {
        ...state,
        projects: action.payload
      };

    case Get_Project:
      return {
        ...state,
        project: action.payload
      };

    case Delete_Project:
      return {
        ...state,
        projects: state.projects.filter(
          project => project.projectIdentifier !== action.payload
        )
      };
    default:
      return state;
  }
}
