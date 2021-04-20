import React from "react";

const Troubleshooting = ({paragraphs}) => {
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

export default Troubleshooting