import { SET_CURRENT_USER } from "../actions/types";

const initialStae = {
  user: {},
  validToken: false
};

const booleanActionPayload = payload => {
  if (payload) {
    return true;
  }
  return false;
};
export default function(state = initialStae, action) {
  switch (action.type) {
    case SET_CURRENT_USER:
      return {
        ...state,
        validToken: booleanActionPayload(action.payload),
        user: action.payload
      };

    default:
      return state;
  }
}
