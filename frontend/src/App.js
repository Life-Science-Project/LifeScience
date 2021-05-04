import './App.css';
import React from "react";
import Header from './components/Header/header';
import {BrowserRouter as Router, Route, withRouter, Switch} from 'react-router-dom';
import Register from "./components/main/Authentication/Register/register";
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from "./components/Navbar/navbar";
import Login from "./components/main/Authentication/Login/login";
import CategoriesContainer from "./components/main/Categories/categoriesContainer";
import MethodContainer from "./components/Method/method-container";
import NewArticle from "./components/NewArticle/NewArticle"
import ProfilePageContainer from "./components/main/ProfilePage/profilePageContainer";
import Home from "./components/main/HomePage/homePage"
import {connect} from "react-redux";
import {getInitDataThunk} from "./redux/init-reducer";
import NotFound from "./components/common/NotFound/notFound";
import Preloader from "./components/common/Preloader/preloader";
import SearchContainer from "./components/Search/search-container";

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
                    <Route path="/search" render={() => <SearchContainer/>}/>
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

