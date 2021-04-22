import React from "react";
import "./advantages-disadvantages.css"

const Advantages = ({content}) => {
    const columnWidth = 250;

    const columnCount = content[0].length;

    const gridColumns = Array(columnCount).fill("").map(() => (columnWidth + "px")).join(" ");

    const style = {
        width: columnWidth * columnCount + "px",
        gridTemplateColumns: gridColumns,
    }



    return (
        <div className="section-content">
            <h6>
                This section is hardcoded and not being pulled from server.
            </h6>
            <div className="advantages-table" style={style}>
                {(content.xSize < 1 || content.ySize < 1)
                    ? "No disadvantages or advantages found"
                    : content.map((arr) => (
                        arr.map((elem) => (
                            <div className="advantages-table-element">
                                {elem === "" ? "-" : elem}
                            </div>
                        ))
                    ))
                }
            </div>
            <button className="btn btn-success compare-methods-button" type="button">Add to compare</button>
        </div>
    )
}

Advantages.defaultProps = {
    content: [
        ["by whom / parameter", "by Admin1", "by Admin2", "by Admin3"],
        ["Cost", "$1 per gram", "$1.5 per gram", ""],
        ["Time", "2 hours", "2.5 hours", "120 minutes"],
        ["Sensitivity", "High", "", "Very High"]
    ]
}


export default Advantages