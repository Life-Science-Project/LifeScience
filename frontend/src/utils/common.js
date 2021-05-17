export const contains = (obj, field) => {
    return obj[field] !== undefined;
};

export const byField = (field) => {
    return (a, b) => a[field] > b[field] ? 1 : -1;
};

export const isEmpty = (array) => {
    return array.length === 0;
};

export const getField = (obj, field) => {
    return obj[field];
};

export const getLineWithSetLen = (length, firstStr) => {
    let res = firstStr.concat('');
    let counter = length - firstStr.length;
    while (counter-- !== 0) {
        res = res.concat(' ');
    }
    return res;
};

export const functionWrapper = (f, ...args) => () => {
    return f(...args);
}

export const containsByField = (data, field, fieldId) => {
    const id = parseInt(fieldId);
    for (let i = 0; i < data.length; i++) {
        if (data[i][field] === id) {
            return true
        }
    }
    return false;
}

export const getUserWrap = (user) => {
    return {
        doctorDegree: user.doctorDegree,
        academicDegree: user.academicDegree,
        organisations: user.organisations.map(x => x.name),
        orcid: user.orcid,
        researchId: user.researchId
    }
}
