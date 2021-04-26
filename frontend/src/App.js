import './App.css';
import React, {useState} from "react";
import Header from './components/Header/header';
import Home from './components/Main/HomePage/homePage';
import Register from "./components/Register/register";
import {BrowserRouter as Router, Route} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar/navbar";
import Method from "./components/Method/method";
import Login from "./components/Login/login";
import CategoriesContainer from "./components/Main/Categories/categoriesContainer";
import MethodContainerWithRouter from "./components/Method/method-container";
import MethodContainer from "./components/Method/method-container";

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
            <Route path="/categories/:categoryId?" render={() => <CategoriesContainer />}/>
            <Route path="/method/:articleId">
                <MethodContainer/>
            </Route>
        </Router>
    );
};

export default App;
