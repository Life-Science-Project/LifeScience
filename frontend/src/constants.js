export const rootUrl = "https://life-science-2021.herokuapp.com";
export const storageUrl = rootUrl + ":storage";
export const LOGIN_URL = "/login"
export const METHOD_URL = "/method"

export const DOCTORS_DEGREE = [
    {value: "PhD", name: "PhD"},
    {value: "NONE", name: "None"}
];

export const ACADEMIC_DEGREE = [
    {value: "ASSOCIATE", name: "Associate"},
    {value: "BACHELOR", name: "Bachelor"},
    {value: "MASTER", name: "Master"},
    {value: "PROFESSIONAL", name: "Professional"},
    {value: "NONE", name: "None"}
]

export const INFO_SECTION_TITLES = ["General information", "Protocol", "Equipment and reagents required", "Application",
    "Method advantages and disadvantages", "Troubleshooting"];

export const AUTO_SECTION_TITLES = ["Find collaboration", "Education"];

export const SECTION_TITLES = INFO_SECTION_TITLES.concat(AUTO_SECTION_TITLES)

export const PROTOCOLS = "Protocols"

export const ROLES = Object.freeze({
    user: "ROLE_USER",
    admin: "ROLE_ADMIN",
    moderator: "ROLE_MODERATOR"
});
