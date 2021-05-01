import React from "react";
import "./advantages-disadvantages.css"
import Page from "../../../Page/Page";

const Advantages = ({contents}) => {
    if (!contents) {
        contents = Advantages.defaultProps.contents
    } else {
        return <Page text={contents.text}/>
    }

    const columnWidth = 250;

    const columnCount = contents[0].length;

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
                {(contents.xSize < 1 || contents.ySize < 1)
                    ? "No disadvantages or advantages found"
                    : contents.map((arr) => (
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
    contents: [
        ["by whom / parameter", "by Admin1", "by Admin2", "by Admin3"],
        ["Cost", "$1 per gram", "$1.5 per gram", ""],
        ["Time", "2 hours", "2.5 hours", "120 minutes"],
        ["Sensitivity", "High", "", "Very High"]
    ]
}


export default Advantages