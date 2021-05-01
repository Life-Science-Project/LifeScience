import React from "react";
import "./developingPage.css";

const DevelopingPage = ({pageName}) => {
    return(
        <div className="developing_container">
            <span>
                Sorry, page {pageName} is developing now, please wait.
            </span>
        </div>
    )
}

export default DevelopingPage;