import React, {useState} from "react";
import './reference-list.css'

const ReferenceList = ({refs}) => {
    const [showList, setShowList] = useState(false)

    function toggleList() {
        setShowList(!showList)
    }

    let listOpenClass = showList ? "list-open" : "list-closed"

    return (
        <div>
            <div className="references-header" onClick={toggleList}>
                <h5>
                    References {String.fromCharCode(showList ? 8593 : 8595)}
                </h5>
            </div>
            {
                showList
                    ?
                    <div className="reference-list">
                        {refs.map((ref, index) => (
                            <section className="reference-list-item">{index + 1}. {ref}</section>
                        ))}
                    </div>
                    :
                    ""

            }
        </div>

    )
}

export default ReferenceList