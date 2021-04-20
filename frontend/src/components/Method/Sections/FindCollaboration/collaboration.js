import React from "react";
import "./collaboration.css"

const Collaboration = () => {
    return (
        <div className="section-content">
            <form className="d-flex justify-content-center flex-column">
                <input type="text" placeholder="Country" name="Country" className="method-form-elem"/>
                <input type="text" placeholder="City" name="City" className="method-form-elem"/>
                <input type="text" placeholder="Organization" name="Organization" className="method-form-elem"/>
                <button type="button" className="btn btn-success method-form-elem">Find</button>
            </form>
        </div>
    )
}

export default Collaboration