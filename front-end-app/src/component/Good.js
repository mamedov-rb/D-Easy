import React, {Component} from 'react'
import {toast} from "react-toastify";
import {connect} from "react-redux";

const BASE_URL = 'http://localhost:8120/api/';
const IMAGE_URL = 'good/download-file/';

class Good extends Component {

    constructor(props) {
        super(props);
        this.state = {
            active: true,
            image: {}
        }
    }

    componentDidMount() {
        fetch(BASE_URL + IMAGE_URL + this.props.el.images[0])
            .then(response => response.blob())
            .then(blob => this.setState({image: URL.createObjectURL(blob)}))
            .catch((err) => {
                toast.error(err.message, {position: toast.POSITION.TOP_RIGHT})
            });
        this.disableIfPresent(this.props.el);
    }

    disableIfPresent = (good) => {
        if (this.props.cart.filter(item => item.id === good.id).length > 0) {
            this.setState({active: false});
        }
    }

    render() {
        let good = this.props.el;
        let goodClicked = () => {
            this.setState({active: false});
            good.quantity = 1;
            this.props.addToCart(good);
        }
        return (
            <div className="ui centered card" onClick={goodClicked}>
                <div className={this.state.active ? "ui image" : "ui disabled image"}>
                    <img src={this.state.image}/>
                </div>
                <div className="content">
                    <div className="header">{good.name}</div>
                </div>
                <div className="extra content">
                    <span className="right floated">
                        <i className="percent icon"/>
                        {good.discount}
                    </span>
                    <span className="left floated">
                        <i className="ruble sign icon"/>
                        <a>{good.price}</a>
                    </span>
                </div>
            </div>
        )
    }

}

export default connect(
    state => ({
        cart: state.cart
    }),
    dispatch => ({
        addToCart: (good) => {
            dispatch({type: 'ADD_TO_CART', payload: good});
        }
    })
)(Good);