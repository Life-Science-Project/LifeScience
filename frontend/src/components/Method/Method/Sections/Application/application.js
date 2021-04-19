import React from "react";

const Application = ({content}) => {
    return (
        <div className="section-content">
            <div className="main-text">
                {
                    content.map((appl) => (
                        <section className="list-item " dangerouslySetInnerHTML={{__html: appl}}/>
                    ))
                }
            </div>
        </div>
    )
}

export default Application