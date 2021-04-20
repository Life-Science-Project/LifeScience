import React from "react";

const Education = () => {
    return (
        <div className="section-content">
            <form className="d-flex justify-content-center flex-column">
                <input type="text" placeholder="Country" name="Country" className="method-form-elem"/>
                <input type="text" placeholder="City" name="City" className="method-form-elem"/>
                <input type="text" placeholder="Time" name="Time" className="method-form-elem"/>
                <input type="text" placeholder="Language" name="Language" className="method-form-elem"/>
                <button type="button" className="btn btn-success method-form-elem">Search</button>
            </form>
        </div>
    )
}

export default Education