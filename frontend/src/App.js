import './App.css';
import React, {useState} from "react";
import Header from './components/Header/header';
import Home from './components/HomePage/homePage';
import Register from "./components/Register/register";
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar/navbar";
import Location from "./components/Location/location";
import Login from "./components/Login/login";
import Method from "./components/Method/method";

function App() {
    const [loggedUser, setLoggedUser] = useState({});
    const subFolders = [{
        path: "/",
        name: "Home",
    }]
    return (
        <Router>
            <Header user={loggedUser}/>
            <Navbar/>
            <Route exact={true} path="/" component={Home}/>
            <Route path="/register" component={Register}/>
            <Route path="/login" render={() => <Login loggedUserStateUpdater={(user) => setLoggedUser(user)}/>}/>
            <Route path="/bradford-assay" component={Method}/>
        </Router>
    );
}

export default App;
