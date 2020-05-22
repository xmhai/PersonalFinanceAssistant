import React from 'react'
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import { fetchPortfolios } from '../actions';
import { fetchCurrencies } from '../../common/actions';
import { fetchCategories } from '../../common/actions';

class PortfolioList extends React.Component {
    componentDidMount() {
        this.props.fetchCurrencies();
        this.props.fetchCategories();
        this.props.fetchPortfolios();
    }

    renderAdmin(portfolio) {
        return (
            <div className="right floated content">
                <Link to={`/portfolios/edit/${portfolio.id}`} className="ui button primary">
                    Edit
                </Link>
                <Link to={`/portfolios/delete/${portfolio.id}`} className="ui button negative" >
                    Delete
                </Link>
            </div>
        );
    }

    renderList() {
        return this.props.portfolios.map(portfolio => {
            return (
                <div className="item" key={portfolio.id}>
                    {this.renderAdmin(portfolio)}
                    <i className="large middle aligned icon camera" />
                    <div className="content">
                        <Link to={`/portfolios/${portfolio.id}`} className="header">
                            {portfolio.instituteName}
                        </Link>
                        <div className="description">{portfolio.portfolioNo}</div>
                        <div className="description">{this.props.categories[portfolio.categoryId].code}</div>
                    </div>
                </div>
            );
        });
    }

    renderCreate() {
        return (
            <div style={{ textAlign: 'right' }}>
                <Link to="/portfolios/new" className="ui button primary">
                    Create Stream
                </Link>
            </div>
        );
    }

    render() {
        return (
            <div>
                <h2>Portfolios</h2>
                {this.renderCreate()}
                <div className="ui celled list">{this.renderList()}</div>
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        portfolios: Object.values(state.portfolios),
        currencies: state.config.currencies,
        categories: state.config.categories
    };
};

export default connect(mapStateToProps, { fetchPortfolios, fetchCurrencies, fetchCategories })(PortfolioList);