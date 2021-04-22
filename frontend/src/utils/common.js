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