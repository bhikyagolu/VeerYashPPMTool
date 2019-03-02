import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async dispatch => {
  try {
    console.log("calling register api");
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    console.log(" got the errors in login response .....");
    console.log(err);
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const login = loginRequest => async dispatch => {
  try {
    console.log(loginRequest);
    // post => login request
    const res = await axios.post("/api/users/login", loginRequest);

    // extract the token from res.data
    const { token } = res.data;
    // store the token into local storage
    localStorage.setItem("jwtToken", token);
    // set our token in header
    setJWTToken(token);
    // decode the token on React
    const decoded = jwt_decode(token);
    // dispatch to our security reducer

    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded
    });
  } catch (err) {
    console.log(" got the errors in login response .....");
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const logout = () => dispatch => {
  localStorage.removeItem("jwtToken");
  setJWTToken(false);
  dispatch({
    type: SET_CURRENT_USER,
    payload: {}
  });
};
