import React from "react";

const Equipment = ({paragraphs}) => {
    return (
        <div className="section-content">
            {
                paragraphs.map((p) => (
                    <div className="paragraph">
                        {p.text}
                    </div>
                ))
            }
        </div>
    )
}

export default Equipment