import './App.css';
import React from "react";
import Header from './components/Header/header';
import Register from "./components/Register/register";
import {BrowserRouter as Router, Route} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar/navbar";
import Login from "./components/Main/Login/login";
import CategoriesContainer from "./components/Main/Categories/categoriesContainer";
import MethodContainer from "./components/Method/method-container";
import Home from "./components/Main/HomePage/homePage";
import NewArticle from "./components/NewArticle/NewArticle";
import UserPage from "./components/Main/UserPage/userPage";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import {getInitDataThunk} from "./redux/init-reducer";

class App extends React.Component {
    componentDidMount() {
        this.props.getInitDataThunk();
    }

    render() {
        return (
            <Router>
                <Header/>
                <Navbar/>
                <Route exact={true} path="/" component={Home}/>
                <Route exact={true} path="/userPage/:userId?" render={() => <UserPage />}/>
                <Route path="/new-article/:categoryId?" component={NewArticle}/>
                <Route path="/register" component={Register}/>
                <Route path="/login" render={() => <Login />}/>
                <Route path="/categories/:categoryId?" render={() => <CategoriesContainer />}/>
                <Route path="/method/:articleId">
                    <MethodContainer/>
                </Route>
            </Router>
        );
    }
}

const mapStateToProps = (state) => {
    return ({
        isInitialized: state.init.isInitialized
    })
}

let WithRouterAppContainer = withRouter(App);

export default connect(mapStateToProps, {getInitDataThunk})(WithRouterAppContainer);

