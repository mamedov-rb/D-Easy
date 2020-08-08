let savedGoodsArr = localStorage.getItem('cart');
const initialState = savedGoodsArr === null ? [] : JSON.parse(savedGoodsArr);

export default function cart(state = initialState, action) {
    if (action.type === 'ADD_TO_CART') {
        let array = [...state, action.payload];
        let filtered = array.filter((value, index, self) => {
            return self.findIndex(v => v.id === value.id) === index;
        });
        localStorage.setItem('cart', JSON.stringify(filtered));
        return filtered;
    }
    if (action.type === 'REMOVE_FROM_CART') {
        let index = state.indexOf(action.payload);
        state.splice(index, 1);
        localStorage.setItem('cart', JSON.stringify(state));
        return state;
    }
    return state;
}
