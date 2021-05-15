import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import store from "./redux/reduxe-store";
import reportWebVitals from './reportWebVitals';
import {Provider} from "react-redux";
import {BrowserRouter} from "react-router-dom";
import ErrorBoundary from "./components/common/ErrorBoundary/ErrorBoundary";

ReactDOM.render(
  <React.StrictMode>
      <BrowserRouter>
          <ErrorBoundary>
              <Provider store={store}>
                  <App/>
              </Provider>
          </ErrorBoundary>
      </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
