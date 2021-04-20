import React from "react";

const Application = ({content}) => {
    return (
        <div className="section-content">
            <h6>
                This section is hardcoded and not being pulled from server.
            </h6>
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

Application.defaultProps = {
    content: ["<a href='/link1'>Quantitative analysis of proteins</a>",
        "<a href='/link2'>Some other application</a>"],
}

export default Application