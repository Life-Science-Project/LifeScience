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
            <button className="btn btn-success compare-methods-button" type="button">Compare with other methods</button>
        </div>
    )
}

Advantages.defaultProps = {
    content : {
        xSize: 0,
        ySize: 0,
    }
}


export default Advantages