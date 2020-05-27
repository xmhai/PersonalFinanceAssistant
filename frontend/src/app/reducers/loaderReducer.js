const loader = (state = {counter: 0}, action) => {
    switch (action.type) {
        case "SHOW_LOADER":
            return { ...state, counter: ++state.counter };
        case "HIDE_LOADER":
            return { ...state, counter: --state.counter };
        default:
            return state;
    }
}

export default loader;
