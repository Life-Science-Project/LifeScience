import GeneralInformation from "./GeneralInformation/general-information";
import Equipment from "./Equipment/equipment";
import Application from "./Application/application";
import Protocol from "./Protocol/protocol";
import Advantages from "./AdvantagesDisadvantages/advantages-disadvantages";
import Troubleshooting from "./Troubleshooting/troubleshooting";
import Collaboration from "./FindCollaboration/collaboration";
import Education from "./Education/education";
import Page from "../../Page/Page";


const Section = (props) => {

    const {name, contents} = props;

    //todo make constants global
    const nameToComponent = {
        "General Information": <GeneralInformation contents={contents}/>,
        "Protocol": <Protocol contents={contents}/>,
        "Equipment and reagents required": <Equipment contents={contents}/>,
        "Application": <Application contents={contents}/>,
        "Method advantages and disadvantages": <Advantages contents={contents}/>,
        "Troubleshooting": <Troubleshooting contents={contents}/>,
        "Find collaboration": <Collaboration contents={contents}/>,
        "Education": <Education contents={contents}/>,
    }

    function getSection() {
        let component = nameToComponent[name];
        if (!component) {
            component = <Page text={contents.text}/>
        }
        return component
    }

    return getSection()

}

export default Section