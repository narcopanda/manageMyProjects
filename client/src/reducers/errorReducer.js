import { Get_Errors } from "../actions/types";

const initState = {};

export default function(state = initState, action) {
  switch (action.type) {
    case Get_Errors:
      return action.payload;

    default:
      return state;
  }
}
