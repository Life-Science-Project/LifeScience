import './App.css';
import React, {useState} from "react";
import Header from './components/Header/header';
import Home from './components/HomePage/homePage';
import Register from "./components/Register/register";
import {BrowserRouter as Router, Route} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar/navbar";
import Method from "./components/Method/method";
import Main from "./components/MainPage/mainPage";
import Switch from "react-bootstrap/Switch";

function App() {
    // Fetch auth-data from local storage in case user was already logged in
    const authData = JSON.parse(localStorage.getItem('auth-data'));
    const [loggedUser, setLoggedUser] = useState(authData ? authData : {});
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
            <Switch>
                <Route exact path="/main" component={Main}/>
                <Route path="/main/:id" component={Main}/>
            </Switch>
            <Route path="/bradford-assay" component={Method}/>
        </Router>
    );
}

export default App;
