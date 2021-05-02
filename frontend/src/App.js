import './App.css';
import React from "react";
import Header from './components/Header/header';
import Register from "./components/main/Register/register";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar/navbar";
import Login from "./components/main/Login/login";
import CategoriesContainer from "./components/main/Categories/categoriesContainer";
import MethodContainer from "./components/Method/method-container";
import Home from "./components/main/HomePage/homePage";
import NewArticle from "./components/NewArticle/NewArticle"
import ProfilePage from "./components/main/ProfilePage/profilePage";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import {getInitDataThunk} from "./redux/init-reducer";
import NotFound from "./components/common/NotFound/notFound";

class App extends React.Component {
    componentDidMount() {
        this.props.getInitDataThunk();
    }

    render() {
        return (
            <Router>
                <Header/>
                <Navbar/>
                <Switch>
                    <Route exact={true} path="/" component={Home}/>
                    <Route path="/profile" render={() => <ProfilePage />}/>
                    <Route path="/new-article/:categoryId?" component={NewArticle}/>
                    <Route path="/register" render={() => <Register/>}/>
                    <Route path="/login" render={() => <Login />}/>
                    <Route path="/categories/:categoryId?" render={() => <CategoriesContainer />}/>
                    <Route path="/notFound" render={() => <NotFound/>}/>
                    <Route path="/method/:articleId" render={() => <MethodContainer/>}/>
                    <Route render={() => <NotFound/>}/>
                </Switch>
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

