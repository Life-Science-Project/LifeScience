import './App.css';
import React, {useState} from "react";
import Header from './components/Header/header';
import Home from './components/HomePage/homePage';
import Register from "./components/Register/register";
import {BrowserRouter as Router, Route} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar/navbar";
import Login from "./components/Login/login";
import Method from "./components/Method/method";

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
            <Route path="/bradford-assay">
                <Method link="/bradford-assay" articleId={1}/>
            </Route>
        </Router>
    );
}

export default App;
