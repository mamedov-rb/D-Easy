export default function goods(state = [], action) {
    if (action.type === 'ADD_GOOD') {
        return [...state, action.payload];
    }
    return state;
}
