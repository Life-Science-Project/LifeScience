import GeneralInformation from "./GeneralInformation/general-information";
import Equipment from "./Equipment/equipment";
import Application from "./Application/application";
import Protocol from "./Protocol/protocol";
import Advantages from "./AdvantagesDisadvantages/advantages-disadvantages";
import Troubleshooting from "./Troubleshooting/troubleshooting";
import Collaboration from "./FindCollaboration/collaboration";
import Education from "./Education/education";
import SectionPage from "../../Page/SectionPage";
import ProtocolList from "./ProtocolList/protocol-list";


const Section = (props) => {

    const {name, contents, articleId} = props;

    //todo make constants global
    //todo make case insensitive
    const nameToComponent = {
        "General information": <GeneralInformation contents={contents}/>,
        "Protocol": <Protocol contents={contents}/>,
        "Equipment and reagents required": <Equipment contents={contents}/>,
        "Application": <Application contents={contents || undefined}/>,
        "Method advantages and disadvantages": <Advantages contents={contents}/>,
        "Troubleshooting": <Troubleshooting contents={contents}/>,
        "Find collaboration": <Collaboration contents={contents}/>,
        "Education": <Education contents={contents}/>,
        "Protocols": <ProtocolList articleId={articleId}/>
    }

    function getSection() {
        let component = nameToComponent[name];
        if (!component) {
            component = <SectionPage text={contents.text}/>
        }
        return component
    }

    return getSection()

}

export default Section