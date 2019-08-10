import { createStore, applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const initState = {};
const middleware = [thunk];

let store;

const ReactReduxDevTools =
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();

if (window.navigator.userAgent.includes("Chrome") && ReactReduxDevTools) {
  store = createStore(
    rootReducer,
    initState,
    compose(
      applyMiddleware(...middleware),
      ReactReduxDevTools
    )
  );
} else if (
  window.navigator.userAgent.includes("Firefox") &&
  ReactReduxDevTools
) {
  store = createStore(
    rootReducer,
    initState,
    compose(
      applyMiddleware(...middleware),
      ReactReduxDevTools
    )
  );
} else {
  store = createStore(
    rootReducer,
    initState,
    compose(applyMiddleware(...middleware))
  );
}

export default store;
