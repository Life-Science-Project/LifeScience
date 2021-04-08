import React from "react";
import './homePage.css'
import {BrowserRouter as Router, Link, Route, Switch} from 'react-router-dom';
import Page from "../Page/Page";

const Home = ({subFolders}) => {
    return(
        // <Router>
        //     <ul>
        //         <li>
        //             <Link to={"/link1"}>Link1</Link>
        //         </li>
        //         <li>
        //             <Link to={"/link2"}>Link2</Link>
        //         </li>
        //     </ul>
        //     <Switch>
        //         <Route path={"/link1"}>
        //             <Page text={"link 1 text"}/>
        //         </Route>
        //         <Route path={"/link2"}>
        //             <Page text={"link 2 other text"}/>
        //         </Route>
        //     </Switch>
        //
        // </Router>
        <div>
            Home
        </div>

    );
}

export default Home;