import './App.css';
import React from "react";
import Header from './components/Header/header';
import Register from "./components/Main/Authentication/Register/register";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar/navbar";
import Login from "./components/Main/Authentication/Login/login";
import MethodContainer from "./components/Method/method-container";
import Home from "./components/Main/HomePage/homePage";
import NewArticle from "./components/NewArticle/NewArticle"
import ProfilePageContainer from "./components/Main/ProfilePage/profilePageContainer";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import {getInitDataThunk} from "./redux/init-reducer";
import NotFound from "./components/common/NotFound/notFound";
import Preloader from "./components/common/Preloader/preloader";
import CategoriesContainer from './components/Main/Categories/categoriesContainer'

class App extends React.Component {
    componentDidMount() {
        this.props.getInitDataThunk();
    }

    render() {
        if (!this.props.isInitialized) {
            return <Preloader/>
        }
        return (
            <Router>
                <Header/>
                <Navbar/>
                <Switch>
                    <Route exact={true} path="/" component={Home}/>
                    <Route path="/profile" render={() => <ProfilePageContainer />}/>
                    <Route path="/new-article/:categoryId?" render={() => <NewArticle/>}/>
                    <Route path="/register" render={() => <Register/>}/>
                    <Route path="/login" render={() => <Login />}/>
                    <Route path="/categories/:categoryId?" render={() => <CategoriesContainer />}/>
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

