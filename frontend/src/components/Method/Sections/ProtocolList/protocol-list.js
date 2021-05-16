import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {clearProtocols, fetchProtocols} from "../../../../redux/actions/protocol-list-actions";
import Preloader from "../../../common/Preloader/preloader";
import {Link} from "react-router-dom";
import {withRouter} from "react-router";
import {METHOD_URL, PROTOCOL} from "../../../../constants";
import {passSectionFunc} from "../../../../redux/method-reducer";


const ProtocolList = ({articleId}) => {

    const dispatch = useDispatch()

    const getProtocols = () => {
        dispatch(fetchProtocols(articleId))
    }

    useEffect(() => {
        getProtocols()

        return () => {
            dispatch(clearProtocols())
        }
    }, [articleId])

    const protocols = useSelector(state => state.protocolList.protocols)
    const isReceived = useSelector(state => state.protocolList.isReceived)

    const handleClick = () => {
        dispatch(passSectionFunc(section => section.name === PROTOCOL))
    }

    if (!isReceived) return <Preloader/>

    return (
        <div className="section-content">
            {protocols.length === 0 && "No protocols found for this method."}
            <ul>
                {
                    protocols.map((protocol) => (
                        <li>
                            <Link to={{pathname: METHOD_URL +"/" + protocol.id, state: {fromMethod: true}}}
                                  onClick={handleClick}>
                                {protocol.name}
                            </Link>
                        </li>
                    ))
                }
            </ul>
        </div>
    )

}

export default withRouter(ProtocolList)