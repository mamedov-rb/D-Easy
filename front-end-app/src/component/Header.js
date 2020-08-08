import React, {Component} from 'react'
import {connect} from "react-redux";
import {Link} from "react-router-dom";

class Header extends Component {

    render() {
        return (
            <h2 className="ui block header">
                <Link to="/cart">
                    <div className="right icon floated">
                        <i className="cart icon"/>
                        <div className="ui red circular tiny label">{this.props.cart.length}</div>
                    </div>
                </Link>
            </h2>
        )
    }

}

export default connect(
    state => ({
        cart: state.cart

    }),
    dispatch => ({
    })
)(Header);