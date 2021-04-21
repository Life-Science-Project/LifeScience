import React from "react";

const Protocol = ({paragraphs}) => {
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

export default Protocol