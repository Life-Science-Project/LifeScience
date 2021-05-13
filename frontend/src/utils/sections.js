import {AUTO_SECTION_TITLES, INFO_SECTION_TITLES, SECTION_TITLES} from "../constants";

export const getSectionsForShow = (sections) => {
    return sortSectionsBy(sections, SECTION_TITLES);
}

//todo rename
export const getSectionsForPreview = (sections) => {
    const sortedSections = sortSectionsBy(sections, INFO_SECTION_TITLES);
    for (const title of AUTO_SECTION_TITLES) {
        sortedSections.push({
            name: title
        })
    }
    return sortedSections
}

const sortSectionsBy = (sections, example) => {
    const sortedSections = [];
    for (const title of example) {
        for (const section of sections) {
            if (section.name === title) {
                sortedSections.push(section)
            }
        }
    }
    return sortedSections;
}