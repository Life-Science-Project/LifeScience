import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {clearProtocols, fetchProtocols} from "../../../../redux/actions/protocol-list-actions";
import Preloader from "../../../common/Preloader/preloader";
import {Link} from "react-router-dom";
import {withRouter} from "react-router";
import {METHOD_URL} from "../../../../constants";


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

    if (!isReceived) return <Preloader/>

    return (
        <div className="section-content">
            <ul>
                {
                    protocols.map((protocol) => (
                        <li>
                            <Link to={METHOD_URL + "/" + protocol.id}>
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