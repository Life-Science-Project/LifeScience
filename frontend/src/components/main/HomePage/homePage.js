import React from "react";
import './homePage.css'

class Home extends React.Component {

    render() {
        return (
            <div className="home_container">
                <h1 className={"home_page__text"}>We are glad to welcome you on the Life Science platform!</h1>
                <br/>
                <br/>
                <h5 className={"home_page__text"}>
                    Here you can find extensive information about various methods used in research and development. You can find the desired method using the search or through the catalog. For each method, general theoretical information, troubleshooting, and required equipment and reagents are provided. Finally, the platform contains actual and proven protocols presented in an apprehensible form!
                </h5>
                <br/>
                <h5 className={"home_page__text"}>
                    Our platform provides you the opportunity to search for specialists for collaboration and the opportunity to learn the techniques of your interest. The information on the platform is regularly updated by the registered users - members of the scientific community. In addition, the platform allows users to communicate with each other, unite in teams, and exchange data.
                </h5>
                <br/>
                <h5 className={"home_page__text"}>
                    Our goal is to make scientific research more relevant, the process of finding and sharing information - easier, and make research more accessible!
                </h5>
                <br/>
                <h5 className={"home_page__text"}>
                    We invite you to join the platform community! Use and replenish the data with us!
                </h5>
            </div>
        );
    }
}


export default Home;